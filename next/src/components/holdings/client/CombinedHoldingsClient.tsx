"use client";

import { useState } from "react";
import PortfolioHeader from "../pure/PortfolioHeader";
import SecurityHoldingsTable from "@/components/client/SecurityHoldingsTable";
import { mapSecurityToHolding } from "@/types/mappers";
import CashHoldingsTable from "@/components/account/cash/pure/CashHoldingsTable";

interface CombinedHoldingsClientProps {
  initialAccounts: Account[];
}

export default function CombinedHoldingsClient({
  initialAccounts,
}: CombinedHoldingsClientProps) {
  const [accounts] = useState<Account[]>(initialAccounts);

  function updateHolding(existing: Holding, security: Security) {
    existing.quantity += security.quantity;
    existing.costBasis += security.costBasis;

    if (security.priceData && existing.currentPrice) {
      existing.marketValue = existing.quantity * existing.currentPrice;
      existing.gain = existing.marketValue - existing.costBasis;
      existing.percentGain =
        existing.costBasis > 0
          ? ((existing.marketValue - existing.costBasis) / existing.costBasis) *
            100
          : 0;
    }
  }

  const combinedHoldings = (() => {
    const holdingsMap = new Map<string, Holding>();

    // Process stock holdings
    accounts.forEach((account: Account) => {
      account.stockHoldings.forEach((stock: Stock) => {
        const key = stock.symbol;
        const existing = holdingsMap.get(key);
        if (existing) {
          updateHolding(existing, stock);
        } else {
          holdingsMap.set(key, mapSecurityToHolding(stock));
        }
      });
    });

    return Array.from(holdingsMap.values());
  })();

  const combinedEtfHoldings = (() => {
    const holdingsMap = new Map<string, Holding>();

    // Process ETF holdings
    accounts.forEach((account) => {
      account.etfHoldings.forEach((etf: Etf) => {
        const key = etf.symbol;
        const existing = holdingsMap.get(key);

        if (existing) {
          updateHolding(existing, etf);
        } else {
          holdingsMap.set(key, mapSecurityToHolding(etf));
        }
      });
    });

    return Array.from(holdingsMap.values());
  })();

  const combinedCashHoldings = (() => {
    const cashMap = new Map<string, Cash>();

    // Process cash holdings
    accounts.forEach((account) => {
      account.cashHoldings.forEach((cash: Cash) => {
        const key = cash.currency;
        const existing = cashMap.get(key);

        if (existing) {
          existing.amount += cash.amount;
          existing.usdValue = (existing.usdValue || 0) + (cash.usdValue || 0);
        } else {
          cashMap.set(key, {
            ...cash,
            id: null, // Reset ID since this is a combined holding
          });
        }
      });
    });

    return Array.from(cashMap.values());
  })();

  const totalCash = accounts.reduce((total, account) => {
    return (
      total +
      account.cashHoldings.reduce((accountTotal, cash) => {
        return accountTotal + cash.usdValue!;
      }, 0)
    );
  }, 0);

  const totalMarketValue = (() => {
    const stockValue = combinedHoldings.reduce((total, holding) => {
      return total + (holding.marketValue || 0);
    }, 0);

    const etfValue = combinedEtfHoldings.reduce((total, holding) => {
      return total + (holding.marketValue || 0);
    }, 0);

    return stockValue + etfValue + totalCash;
  })();

  const totalGain = (() => {
    const stockGain = combinedHoldings.reduce((total, holding) => {
      return total + (holding.gain || 0);
    }, 0);

    const etfGain = combinedEtfHoldings.reduce((total, holding) => {
      return total + (holding.gain || 0);
    }, 0);

    return stockGain + etfGain;
  })();

  return (
    <div className="space-y-8">
      <PortfolioHeader
        totalMarketValue={totalMarketValue}
        totalGain={totalGain}
        totalCash={totalCash}
      />

      {/* Stock Holdings */}
      {combinedHoldings.length > 0 && (
        <SecurityHoldingsTable
          holdings={combinedHoldings}
          title={`Stocks (${combinedHoldings.length} positions)`}
        />
      )}

      {/* ETF Holdings */}
      {combinedEtfHoldings.length > 0 && (
        <SecurityHoldingsTable
          holdings={combinedEtfHoldings}
          title={`ETFs (${combinedEtfHoldings.length} positions)`}
        />
      )}

      <CashHoldingsTable title="Combined Cash" cash={combinedCashHoldings} />
    </div>
  );
}
