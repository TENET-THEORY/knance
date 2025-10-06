"use client";

export default function DatabaseLoading() {
  return (
    <div className="bg-amber-50 border border-amber-200 rounded-lg p-4 mb-4">
      <div className="flex items-center">
        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-amber-600 mr-3"></div>
        <span className="text-amber-800 font-medium">Loading accounts from database...</span>
      </div>
    </div>
  );
}
