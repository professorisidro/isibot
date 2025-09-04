package br.com.isiflix.isibot.services;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

import java.util.List;

import br.com.isiflix.isibot.utils.Utils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class RagService {

	private static final ChatModel CHAT_MODEL = OpenAiChatModel.builder().apiKey(Utils.OPENAI_API_KEY)
			.modelName(GPT_4_O_MINI).build();

	public IsiAssistant doRag() {
		List<Document> documents = loadDocuments(Utils.toPath("./documents"), Utils.glob("*.txt"));
		return AiServices.builder(IsiAssistant.class).chatModel(CHAT_MODEL) // it should use OpenAI
																								// LLM
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10)) // it should remember 10 latest messages
				.contentRetriever(createContentRetriever(documents)) // it should have access to our documents
				.build();
	}

	private static ContentRetriever createContentRetriever(List<Document> documents) {

		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		EmbeddingStoreIngestor.ingest(documents, embeddingStore);
		return EmbeddingStoreContentRetriever.from(embeddingStore);
	}
}
