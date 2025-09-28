"use client";

import { useState } from "react";
import { AccountsViewModel } from "@common/api";
import AccountHeader from "@/components/account/client/AccountHeader";
import PortfolioSummary from "@/components/account/pure/PortfolioSummary";
import SecurityHoldingsTable from "@/components/client/SecurityHoldingsTable";
import CashHoldingsTable from "@/components/account/cash/pure/CashHoldingsTable";
import { mapAccount, mapSecurityToHolding } from "@/types/mappers";

interface AccountPageClientProps {
  initialAccount: Account;
  accountId: number;
}

export default function AccountPageClient({
  initialAccount,
  accountId,
}: AccountPageClientProps) {
  const [account, setAccount] = useState<Account>(initialAccount);
  const vm = new AccountsViewModel();

  const handleEtfAdded = async () => {
    try {
      const updatedAccount = await vm.fetchAccount(accountId);
      setAccount(mapAccount(updatedAccount));
    } catch (error) {
      console.error("Failed to refresh account data:", error);
    }
  };

  const stockHoldings = account.stockHoldings.map(
    (security: Stock): Holding => mapSecurityToHolding(security),
  );

  const etfHoldings = account.etfHoldings.map(
    (security: Etf): Holding => mapSecurityToHolding(security),
  );

  const cashHoldings = account.cashHoldings;

  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-gray-50 dark:bg-gray-900">
      <div className="max-w-7xl mx-auto">
        <AccountHeader
          accountName={account.name}
          accountId={accountId}
          onEtfAdded={handleEtfAdded}
        />
        <PortfolioSummary account={account} />
        <div className="space-y-8">
          {stockHoldings.length > 0 && (
            <SecurityHoldingsTable
              holdings={stockHoldings}
              title="Stock Holdings"
            />
          )}
          {etfHoldings.length > 0 && (
            <SecurityHoldingsTable
              holdings={etfHoldings}
              title="ETF Holdings"
            />
          )}
          {cashHoldings.length > 0 && (
            <CashHoldingsTable cash={cashHoldings} title="Cash Holdings" />
          )}
        </div>
      </div>
    </div>
  );
}
