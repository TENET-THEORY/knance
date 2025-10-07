"use client";

interface QuoteUpdatesProps {
  bulkQuoteMessage: BulkQuoteMessage;
}

export default function QuoteUpdates({ bulkQuoteMessage }: QuoteUpdatesProps) {
  const getMessageContent = () => {
    switch (bulkQuoteMessage.type) {
      case "LoadedCachedQuotes":
        return {
          title: "Cached Data Loaded",
          message: `Loaded ${bulkQuoteMessage.quotes?.length || 0} cached quotes`,
          color: "green"
        };
      case "SymbolsToRefresh":
        return {
          title: "Refreshing Data",
          message: `Updating ${bulkQuoteMessage.symbols?.length || 0} symbols`,
          color: "yellow"
        };
      case "UpdatedQuotes":
        return {
          title: "Data Updated",
          message: `Updated ${bulkQuoteMessage.quotes?.length || 0} quotes`,
          color: "green"
        };
      default:
        return {
          title: "Market Data Update",
          message: "Processing market data...",
          color: "blue"
        };
    }
  };

  const { title, message, color } = getMessageContent();
  
  const colorClasses = {
    green: "bg-green-50 border-green-200 text-green-800",
    yellow: "bg-yellow-50 border-yellow-200 text-yellow-800",
    blue: "bg-blue-50 border-blue-200 text-blue-800"
  };

  return (
    <div className={`border rounded-lg p-4 mb-4 ${colorClasses[color as keyof typeof colorClasses]}`}>
      <div className="flex items-center">
        <div className="w-2 h-2 bg-current rounded-full mr-3 animate-pulse"></div>
        <div>
          <div className="font-medium">{title}</div>
          <div className="text-sm opacity-75">{message}</div>
        </div>
      </div>
    </div>
  );
}
