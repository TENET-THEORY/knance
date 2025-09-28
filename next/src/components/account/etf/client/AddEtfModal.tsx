"use client";

import { useState } from "react";
import { AccountsViewModel, CreateEtfRequest } from "@common/api";

interface AddEtfModalProps {
  isOpen: boolean;
  onClose: () => void;
  accountId: number;
  onEtfAdded: () => void;
}

const ASSET_TYPES = ["STOCKS", "BONDS", "T_BILLS", "GOLD", "OIL"];

export default function AddEtfModal({
  isOpen,
  onClose,
  accountId,
  onEtfAdded,
}: AddEtfModalProps) {
  const [formData, setFormData] = useState({
    symbol: "",
    quantity: "",
    costBasis: "",
    assetType: "STOCKS",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const vm = new AccountsViewModel();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      const request: CreateEtfRequest = new CreateEtfRequest(
        formData.symbol.toUpperCase(),
        parseFloat(formData.quantity),
        parseFloat(formData.costBasis),
        formData.assetType,
      );

      await vm.createEtf(accountId, request);
      onEtfAdded();
      onClose();
      setFormData({
        symbol: "",
        quantity: "",
        costBasis: "",
        assetType: "STOCKS",
      });
    } catch (err) {
      setError("Failed to add ETF. Please try again.");
      console.error("Error adding ETF:", err);
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
            Add ETF
          </h2>
          <button
            onClick={onClose}
            className="text-gray-600 hover:text-gray-800 dark:text-gray-400 dark:hover:text-gray-200 text-xl font-bold leading-none p-1 rounded hover:bg-gray-100 dark:hover:bg-gray-700"
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="symbol"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Symbol
            </label>
            <input
              type="text"
              id="symbol"
              name="symbol"
              value={formData.symbol}
              onChange={handleInputChange}
              required
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="e.g., SPY"
            />
          </div>

          <div>
            <label
              htmlFor="quantity"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Quantity
            </label>
            <input
              type="number"
              id="quantity"
              name="quantity"
              value={formData.quantity}
              onChange={handleInputChange}
              required
              min="0"
              step="0.01"
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="e.g., 100"
            />
          </div>

          <div>
            <label
              htmlFor="costBasis"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Cost Basis
            </label>
            <input
              type="number"
              id="costBasis"
              name="costBasis"
              value={formData.costBasis}
              onChange={handleInputChange}
              required
              min="0"
              step="0.01"
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="e.g., 45000.00"
            />
          </div>

          <div>
            <label
              htmlFor="assetType"
              className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1"
            >
              Asset Type
            </label>
            <select
              id="assetType"
              name="assetType"
              value={formData.assetType}
              onChange={handleInputChange}
              className="w-full px-3 py-2 border border-gray-400 dark:border-gray-600 bg-white text-gray-900 placeholder-gray-500 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            >
              {ASSET_TYPES.map((type) => (
                <option key={type} value={type}>
                  {type}
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
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="flex-1 px-4 py-2 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-400 text-white rounded-md transition-colors"
            >
              {isLoading ? "Adding..." : "Add ETF"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
