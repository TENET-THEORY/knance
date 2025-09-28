interface PortfolioSummaryProps {
  account: Account;
}

export default function PortfolioSummary({ account }: PortfolioSummaryProps) {
  const calculatePortfolioTotal = () => {
    const cashTotal = account.cashHoldings.reduce(
      (sum, cash) => sum + cash.usdValue!,
      0,
    );
    const stocksTotal = account.stockHoldings.reduce(
      (sum, stock) => sum + (stock.priceData?.marketValue || stock.costBasis),
      0,
    );
    const etfsTotal = account.etfHoldings.reduce(
      (sum, etf) => sum + (etf.priceData?.marketValue || etf.costBasis),
      0,
    );
    const creditCardsTotal = account.creditCards.reduce(
      (sum, card) => sum + card.balance,
      0,
    );

    return {
      portfolioTotal: cashTotal + stocksTotal + etfsTotal + creditCardsTotal,
      cash: cashTotal,
      stocks: stocksTotal,
      etfs: etfsTotal,
      creditCards: creditCardsTotal,
    };
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(value);
  };

  const portfolioData = calculatePortfolioTotal();

  return (
    <div className="grid grid-cols-5 gap-4 mb-8">
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 shadow-sm">
        <div className="text-sm text-gray-500 dark:text-gray-400 mb-1">
          Portfolio Total
        </div>
        <div className="text-2xl font-bold text-gray-800 dark:text-white">
          {formatCurrency(portfolioData.portfolioTotal)}
        </div>
      </div>
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 shadow-sm">
        <div className="text-sm text-gray-500 dark:text-gray-400 mb-1">
          Cash
        </div>
        <div className="text-2xl font-bold text-gray-800 dark:text-white">
          {formatCurrency(portfolioData.cash)}
        </div>
      </div>
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 shadow-sm">
        <div className="text-sm text-gray-500 dark:text-gray-400 mb-1">
          Stocks
        </div>
        <div className="text-2xl font-bold text-gray-800 dark:text-white">
          {formatCurrency(portfolioData.stocks)}
        </div>
      </div>
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 shadow-sm">
        <div className="text-sm text-gray-500 dark:text-gray-400 mb-1">
          ETFs
        </div>
        <div className="text-2xl font-bold text-gray-800 dark:text-white">
          {formatCurrency(portfolioData.etfs)}
        </div>
      </div>
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 shadow-sm">
        <div className="text-sm text-gray-500 dark:text-gray-400 mb-1">
          Credit Cards
        </div>
        <div className="text-2xl font-bold text-red-600 dark:text-red-400">
          {formatCurrency(portfolioData.creditCards)}
        </div>
      </div>
    </div>
  );
}
