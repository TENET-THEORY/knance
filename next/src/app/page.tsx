import Link from "next/link";

export default function Home() {
  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-white dark:bg-gray-900">
      <div className="max-w-4xl mx-auto text-center">
        <h1 className="text-4xl font-bold mb-8 text-gray-900 dark:text-white">
          Finance Dashboard
        </h1>
        <p className="text-xl text-gray-600 dark:text-gray-300 mb-12">
          Welcome to your personal finance management system
        </p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-4xl mx-auto">
          <Link
            href="/accounts"
            className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-200 border border-gray-200 dark:border-gray-700 hover:border-blue-300 dark:hover:border-blue-500"
          >
            <div className="text-2xl font-semibold text-gray-800 dark:text-white mb-2">
              Accounts
            </div>
            <div className="text-gray-600 dark:text-gray-300">
              View and manage your financial accounts
            </div>
          </Link>

          <Link
            href="/upload"
            className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-200 border border-gray-200 dark:border-gray-700 hover:border-green-300 dark:hover:border-green-500"
          >
            <div className="text-2xl font-semibold text-gray-800 dark:text-white mb-2">
              Upload Data
            </div>
            <div className="text-gray-600 dark:text-gray-300">
              Upload CSV files from IBKR or Schwab
            </div>
          </Link>

          <div className="p-6 bg-gray-100 dark:bg-gray-700 rounded-lg border border-gray-200 dark:border-gray-600 opacity-60">
            <div className="text-2xl font-semibold text-gray-600 dark:text-gray-400 mb-2">
              Portfolio
            </div>
            <div className="text-gray-500 dark:text-gray-500">
              Coming soon - portfolio analysis and insights
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
