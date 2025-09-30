"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Navigation() {
  const pathname = usePathname();

  return (
    <nav className="flex-none bg-white dark:bg-gray-900 shadow-sm border-b border-gray-200 dark:border-gray-700">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex">
            <Link
              href="/"
              className="flex items-center px-4 py-2 text-lg font-semibold text-gray-900 dark:text-white hover:text-blue-600 dark:hover:text-blue-400 transition-colors"
            >
              knance
            </Link>
          </div>

          <div className="flex space-x-8">
            <Link
              href="/"
              className={`inline-flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                pathname === "/"
                  ? "text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20"
                  : "text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-800"
              }`}
            >
              Dashboard
            </Link>
            <Link
              href="/accounts"
              className={`inline-flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                pathname === "/accounts"
                  ? "text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20"
                  : "text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-800"
              }`}
            >
              Accounts
            </Link>
            <Link
              href="/holdings"
              className={`inline-flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                pathname === "/holdings"
                  ? "text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20"
                  : "text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-800"
              }`}
            >
              Holdings
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
}
