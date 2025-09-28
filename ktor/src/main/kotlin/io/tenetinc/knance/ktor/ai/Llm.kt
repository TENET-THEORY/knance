package io.tenetinc.knance.ktor.ai

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.all.simpleOpenAIExecutor

class Llm(apiKey: String, systemPrompt: String) {

  private val agent =
      AIAgent(
          executor = simpleOpenAIExecutor(apiKey),
          systemPrompt = systemPrompt,
          llmModel = OpenAIModels.Chat.GPT4o)

  suspend fun prompt(input: String): String {
    return agent.run(input)
  }
}
