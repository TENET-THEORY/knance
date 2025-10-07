"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import AddCashModal from "../cash/client/AddCashModal";
import AddEtfModal from "../etf/client/AddEtfModal";

interface AccountHeaderProps {
  accountName: string;
  accountId: number;
  onEtfAdded: () => void;
  onCashAdded: () => void;
}

export default function AccountHeader({
  accountName,
  accountId,
  onEtfAdded,
  onCashAdded,
}: AccountHeaderProps) {
  const router = useRouter();
  const [isAddEtfModalOpen, setIsAddEtfModalOpen] = useState(false);
  const [isAddCashModalOpen, setIsAddCashModalOpen] = useState(false);

  return (
    <div className="flex justify-between items-center mb-8">
      <div className="flex items-center gap-4">
        <button
          onClick={() => router.back()}
          className="text-gray-600 dark:text-gray-400 hover:text-gray-800 dark:hover:text-gray-200 transition-colors"
        >
          ← Back
        </button>
        <h1 className="text-3xl font-bold text-gray-800 dark:text-white">
          {accountName}
        </h1>
      </div>
      <div className="flex gap-3">
        <button
          onClick={() => setIsAddCashModalOpen(true)}
          className="bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-800 text-white font-medium py-2 px-4 rounded-lg transition-colors"
        >
          Add Cash
        </button>
        <button
          onClick={() => setIsAddEtfModalOpen(true)}
          className="bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-800 text-white font-medium py-2 px-4 rounded-lg transition-colors"
        >
          Add ETF
        </button>
        <button className="bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-800 text-white font-medium py-2 px-4 rounded-lg transition-colors">
          Add Credit Card
        </button>
        <button className="bg-red-600 dark:bg-red-700 hover:bg-red-700 dark:hover:bg-red-800 text-white font-medium py-2 px-4 rounded-lg transition-colors">
          Delete Account
        </button>
      </div>

      <AddCashModal
        isOpen={isAddCashModalOpen}
        onClose={() => setIsAddCashModalOpen(false)}
        accountId={accountId}
        onCashAdded={onCashAdded}
      />
      <AddEtfModal
        isOpen={isAddEtfModalOpen}
        onClose={() => setIsAddEtfModalOpen(false)}
        accountId={accountId}
        onEtfAdded={onEtfAdded}
      />
    </div>
  );
}
