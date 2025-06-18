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
		System.out.println("DEBUG - "+documents);

		
		System.out.println("DEBUG - "+GPT_4_O_MINI);
		System.out.println("DEBUG - "+Utils.OPENAI_API_KEY);
		// Second, let's create an assistant that will have access to our documents
		IsiAssistant assistant = AiServices.builder(IsiAssistant.class).chatModel(CHAT_MODEL) // it should use OpenAI
																								// LLM
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10)) // it should remember 10 latest messages
				.contentRetriever(createContentRetriever(documents)) // it should have access to our documents
				.build();

		// Lastly, let's start the conversation with the assistant. We can ask questions
		// like:
		// - Can I cancel my reservation?
		// - I had an accident, should I pay extra?
		return assistant;
	}

	private static ContentRetriever createContentRetriever(List<Document> documents) {

		// Here, we create an empty in-memory store for our documents and their
		// embeddings.
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

		// Here, we are ingesting our documents into the store.
		// Under the hood, a lot of "magic" is happening, but we can ignore it for now.
		EmbeddingStoreIngestor.ingest(documents, embeddingStore);

		// Lastly, let's create a content retriever from an embedding store.
		return EmbeddingStoreContentRetriever.from(embeddingStore);
	}
}
