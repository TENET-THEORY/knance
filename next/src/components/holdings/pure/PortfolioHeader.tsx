interface PortfolioHeaderProps {
  totalMarketValue: number;
  totalGain: number;
  totalCash: number;
}

export default function PortfolioHeader({
  totalMarketValue,
  totalGain,
  totalCash,
}: PortfolioHeaderProps) {
  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(value);
  };

  const getValueColor = (value: number) => {
    return value >= 0 ? "text-green-600" : "text-red-600";
  };

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6">
      <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-4">
        Combined Holdings
      </h1>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="text-center">
          <div className="text-2xl font-bold text-gray-900 dark:text-white">
            {formatCurrency(totalMarketValue)}
          </div>
          <div className="text-sm text-gray-500 dark:text-gray-400">
            Total Market Value
          </div>
        </div>
        <div className="text-center">
          <div className={`text-2xl font-bold ${getValueColor(totalGain)}`}>
            {formatCurrency(totalGain)}
          </div>
          <div className="text-sm text-gray-500 dark:text-gray-400">
            Total Gain/Loss
          </div>
        </div>
        <div className="text-center">
          <div className="text-2xl font-bold text-gray-900 dark:text-white">
            {formatCurrency(totalCash)}
          </div>
          <div className="text-sm text-gray-500 dark:text-gray-400">
            Total Cash
          </div>
        </div>
      </div>
    </div>
  );
}
