"use client";

import { useState } from "react";
import { AccountsViewModel, CreateCashRequest } from "@common/api";

interface AddCashModalProps {
  isOpen: boolean;
  onClose: () => void;
  accountId: number;
  onCashAdded: () => void;
}

const CURRENCIES = ["USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "TWD", "THB"];

export default function AddCashModal({
  isOpen,
  onClose,
  accountId,
  onCashAdded,
}: AddCashModalProps) {
  const [formData, setFormData] = useState({
    amount: "",
    currency: "USD",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const vm = new AccountsViewModel();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      const request: CreateCashRequest = new CreateCashRequest(
        parseFloat(formData.amount),
        formData.currency,
      );

      await vm.createCash(accountId, request);
      onCashAdded();
      onClose();
      setFormData({
        amount: "",
        currency: "USD",
      });
    } catch (err) {
      setError("Failed to add cash. Please try again.");
      console.error("Error adding cash:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-transparent flex items-center justify-center z-50">
      <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-lg p-6 w-full max-w-md mx-4 shadow-xl">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold text-gray-800 dark:text-white">
            Add Cash
          </h2>
          <button
            onClick={onClose}
            className="text-gray-600 hover:text-gray-800 dark:text-gray-400 dark:hover:text-gray-200 text-xl font-bold leading-none p-1 rounded hover:bg-gray-100 dark:hover:bg-gray-700"
            disabled={isLoading}
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="amount"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Amount
            </label>
            <input
              type="number"
              id="amount"
              name="amount"
              value={formData.amount}
              onChange={handleInputChange}
              step="0.01"
              min="0"
              required
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="Enter amount"
              disabled={isLoading}
            />
          </div>

          <div>
            <label
              htmlFor="currency"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Currency
            </label>
            <select
              id="currency"
              name="currency"
              value={formData.currency}
              onChange={handleInputChange}
              required
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              disabled={isLoading}
            >
              {CURRENCIES.map((currency) => (
                <option key={currency} value={currency}>
                  {currency}
                </option>
              ))}
            </select>
          </div>

          {error && (
            <div className="text-red-600 dark:text-red-400 text-sm">
              {error}
            </div>
          )}

          <div className="flex gap-3 pt-4">
            <button
              type="button"
              onClick={onClose}
              className="flex-1 px-4 py-2 text-gray-800 dark:text-gray-300 bg-gray-300 dark:bg-gray-600 hover:bg-gray-400 dark:hover:bg-gray-500 rounded-md transition-colors"
              disabled={isLoading}
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={isLoading || !formData.amount}
              className="flex-1 px-4 py-2 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-400 text-white rounded-md transition-colors"
            >
              {isLoading ? "Adding..." : "Add Cash"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
