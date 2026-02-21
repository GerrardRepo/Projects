#Import Core, Choice of model(in this case, openai), agents to read the steam, and env.
from langchain_core.messages import HumanMessage
from langchain_openai import ChatOpenAI
from langchain.agents import create_agent
from dotenv import load_dotenv

#Read API key (NOTE .env is not commited to github, self setup necessary.)
load_dotenv()

def main():
    # Explicitly set model name and create an agent for it.
    model = ChatOpenAI(model="gpt-5-mini", temperature=0)
    agent = create_agent(model=model, tools=[])

    print("Welcome!")

    while True:
        user_input = input("Enter your query (Enter quit to exit): ").strip()

        #Exit the chatbot
        if user_input.lower() == "quit":
            break
        if not user_input:
            continue

        print("Assistant: ", end="")

        try:
            for token, metadata in agent.stream(
                {"messages": [HumanMessage(content=user_input)]},
                stream_mode="messages",
            ):
                if metadata.get("langgraph_node") == "model": #This prevents printing of tools, debug etc.
                    text = token.text if hasattr(token, "text") else str(token.content)
                    if text:
                        print(text, end="", flush=True)
            print()
        
        #Catches all exceptions, stores as e and display error.
        except Exception as e:
            print(f"\n[Error] {e}")

if __name__ == "__main__":
    main()