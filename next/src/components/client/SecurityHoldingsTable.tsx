"use client";

import { useState, useMemo } from "react";

interface SecurityHoldingsTableProps {
  holdings: Holding[];
  title: string;
}

type SortField = keyof Holding;
type SortDirection = "asc" | "desc";

export default function SecurityHoldingsTable({
  holdings,
  title,
}: SecurityHoldingsTableProps) {
  const [sortField, setSortField] = useState<SortField>("marketValue");
  const [sortDirection, setSortDirection] = useState<SortDirection>("desc");

  const sortedHoldings = useMemo(() => {
    return [...holdings].sort((a, b) => {
      const aValue = a[sortField];
      const bValue = b[sortField];

      if (aValue === null || aValue === undefined) return 1;
      if (bValue === null || bValue === undefined) return -1;

      let comparison = 0;
      if (typeof aValue === "string" && typeof bValue === "string") {
        comparison = aValue.localeCompare(bValue);
      } else if (typeof aValue === "number" && typeof bValue === "number") {
        comparison = aValue - bValue;
      }

      return sortDirection === "asc" ? comparison : -comparison;
    });
  }, [holdings, sortField, sortDirection]);

  const handleSort = (field: SortField) => {
    if (sortField === field) {
      setSortDirection(sortDirection === "asc" ? "desc" : "asc");
    } else {
      setSortField(field);
      setSortDirection("desc");
    }
  };

  const getSortIcon = (field: SortField) => {
    if (sortField !== field) return "↕";
    return sortDirection === "asc" ? "↑" : "↓";
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(value);
  };

  const formatPercent = (value: number) => {
    return `${value >= 0 ? "+" : ""}${value.toFixed(2)}%`;
  };

  const getValueColor = (value: number, isPercent = false) => {
    if (isPercent) {
      return value >= 0 ? "text-green-600" : "text-red-600";
    }
    return value >= 0
      ? "text-green-600"
      : value < 0
        ? "text-red-600"
        : "text-gray-900";
  };

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-sm overflow-hidden">
      <div className="px-6 py-4 border-b border-gray-200 dark:border-gray-700">
        <h2 className="text-xl font-bold text-gray-800 dark:text-white">
          {title}
        </h2>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full">
          <thead className="bg-gray-50 dark:bg-gray-700">
            <tr>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("symbol")}
              >
                SYMBOL {getSortIcon("symbol")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("quantity")}
              >
                QUANTITY {getSortIcon("quantity")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("costBasis")}
              >
                COST BASIS {getSortIcon("costBasis")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("currentPrice")}
              >
                CURRENT PRICE {getSortIcon("currentPrice")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("marketValue")}
              >
                MARKET VALUE {getSortIcon("marketValue")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("gain")}
              >
                GAIN/LOSS {getSortIcon("gain")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("percentGain")}
              >
                G/L % {getSortIcon("percentGain")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("dailyGain")}
              >
                DAILY G/L {getSortIcon("dailyGain")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("dailyPercentGain")}
              >
                DAILY G/L % {getSortIcon("dailyPercentGain")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("divYield")}
              >
                DIV YIELD {getSortIcon("divYield")}
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                ANNUAL DIV
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("peRatio")}
              >
                P/E RATIO {getSortIcon("peRatio")}
              </th>
              <th
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-600"
                onClick={() => handleSort("earningsYield")}
              >
                EARNINGS Y {getSortIcon("earningsYield")}
              </th>
            </tr>
          </thead>
          <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
            {sortedHoldings.map((holding, index) => (
              <tr
                key={index}
                className="hover:bg-gray-50 dark:hover:bg-gray-700"
              >
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white">
                  {holding.symbol}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.quantity.toLocaleString()}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {formatCurrency(holding.costBasis)}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.currentPrice
                    ? formatCurrency(holding.currentPrice)
                    : "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.marketValue
                    ? formatCurrency(holding.marketValue)
                    : "N/A"}
                </td>
                <td
                  className={`px-6 py-4 whitespace-nowrap text-sm font-medium ${holding.gain ? getValueColor(holding.gain) : "text-gray-900 dark:text-white"}`}
                >
                  {holding.gain ? formatCurrency(holding.gain) : "N/A"}
                </td>
                <td
                  className={`px-6 py-4 whitespace-nowrap text-sm font-medium ${holding.percentGain ? getValueColor(holding.percentGain, true) : "text-gray-900 dark:text-white"}`}
                >
                  {holding.percentGain
                    ? formatPercent(holding.percentGain)
                    : "N/A"}
                </td>
                <td
                  className={`px-6 py-4 whitespace-nowrap text-sm font-medium ${holding.dailyGain ? getValueColor(holding.dailyGain) : "text-gray-900 dark:text-white"}`}
                >
                  {holding.dailyGain
                    ? formatCurrency(holding.dailyGain)
                    : "N/A"}
                </td>
                <td
                  className={`px-6 py-4 whitespace-nowrap text-sm font-medium ${holding.dailyPercentGain ? getValueColor(holding.dailyPercentGain, true) : "text-gray-900 dark:text-white"}`}
                >
                  {holding.dailyPercentGain
                    ? formatPercent(holding.dailyPercentGain)
                    : "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.divYield ? `${holding.divYield.toFixed(2)}%` : "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.divYield && holding.currentPrice
                    ? formatCurrency(
                        (holding.divYield / 100) *
                          holding.currentPrice *
                          holding.quantity,
                      )
                    : "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.peRatio ? holding.peRatio.toFixed(2) : "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                  {holding.earningsYield
                    ? `${holding.earningsYield.toFixed(2)}%`
                    : "N/A"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
