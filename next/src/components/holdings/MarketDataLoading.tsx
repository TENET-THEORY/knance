"use client";

export default function MarketDataLoading() {
  return (
    <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-4">
      <div className="flex items-center">
        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-3"></div>
        <span className="text-blue-800 font-medium">Loading cached market data...</span>
      </div>
    </div>
  );
}
