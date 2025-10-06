"use client"

import CombinedHoldingsClient from "@/components/holdings/client/CombinedHoldingsClient";
import MarketDataLoading from "@/components/holdings/MarketDataLoading";
import QuoteUpdates from "@/components/holdings/QuoteUpdates";
import DatabaseLoading from "@/components/holdings/DatabaseLoading";
import {useEffect, useState} from "react";

export default function CombinedHoldingsPage() {
  const [ accounts, setAccounts ] = useState<Account[]>([]);
  const [ currentMessage, setCurrentMessage ] = useState<AllAccountsWithHoldingsMessage | null>(null);
  const [ isLoading, setIsLoading ] = useState(true);

  useEffect(() => {
    setupSocket();
  }, [])

  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-gray-50 dark:bg-gray-900">
      <div className="max-w-7xl mx-auto">
        {/* Show loading indicator when initially loading */}
        {isLoading && accounts.length === 0 && !currentMessage && (
          <div className="bg-gray-100 border border-gray-200 rounded-lg p-8 mb-4">
            <div className="flex items-center justify-center">
              <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-gray-600 mr-3"></div>
              <span className="text-gray-600 font-medium">Connecting to server...</span>
            </div>
          </div>
        )}

        {/* Show database loading indicator */}
        {currentMessage?.type === "AllAccountsWithHoldingsLoadingFromDatabaseMessage" && (
          <DatabaseLoading />
        )}

        {/* Show market data loading indicator */}
        {currentMessage?.type === "AllAccountsWithHoldingsBulkQuoteMessage" && 
         currentMessage.bulkQuoteMessage?.type === "LoadingCachedQuotes" && (
          <MarketDataLoading />
        )}

        {/* Show quote updates */}
        {currentMessage?.type === "AllAccountsWithHoldingsBulkQuoteMessage" && 
         currentMessage.bulkQuoteMessage && 
         currentMessage.bulkQuoteMessage.type !== "LoadingCachedQuotes" && (
          <QuoteUpdates bulkQuoteMessage={currentMessage.bulkQuoteMessage} />
        )}

        {/* Show holdings when we have accounts */}
        {accounts.length > 0 && (
          <CombinedHoldingsClient accounts={accounts} />
        )}
      </div>
    </div>
  );

  function setupSocket() {
    let serverURL = 'ws://0.0.0.0:8080/accounts/full';
    let socket = new WebSocket(serverURL);

    socket.onopen = logOpenToConsole;
    socket.onclose = logCloseToConsole;
    socket.onmessage = handleMessage;
  }

  function handleMessage(event: MessageEvent<string>) {
    console.log(`Received: ${event.data}`);
    
    try {
      const message: AllAccountsWithHoldingsMessage = JSON.parse(event.data);
      setCurrentMessage(message);
      
      switch (message.type) {
        case "AllAccountsWithHoldingsLoadingFromDatabaseMessage":
          // Show database loading indicator
          console.log("Loading accounts from database...");
          break;
          
        case "AllAccountsWithHoldingsWithoutMarketDataMessage":
          if (message.accounts) {
            setAccounts(message.accounts);
            setIsLoading(false);
          }
          break;
          
        case "AllAccountsWithHoldingsBulkQuoteMessage":
          // Keep current accounts, just show the quote update
          console.log("Quote update received:", message.bulkQuoteMessage);
          break;
          
        case "AllAccountsWithMarketDataHoldingsMessage":
          if (message.accounts) {
            setAccounts(message.accounts);
            setIsLoading(false);
          }
          break;
          
        default:
          console.log("Unknown message type:", message.type);
      }
    } catch (error) {
      console.error("Error parsing WebSocket message:", error);
    }
  }

  function logCloseToConsole() {
    console.log("Web socket connection closed");
  }

  function logOpenToConsole() {
    console.log("Web socket connection opened");
  }
}
