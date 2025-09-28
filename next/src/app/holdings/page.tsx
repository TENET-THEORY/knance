import { AccountsViewModel } from "@common/api";
import CombinedHoldingsClient from "@/components/holdings/client/CombinedHoldingsClient";
import { mapAccount } from "@/types/mappers";

export default async function CombinedHoldingsPage() {
  const vm = new AccountsViewModel();
  const accounts = await vm
    .fetchAccountsWithHoldings()
    .then((accounts) => Array.from(accounts.asJsReadonlyArrayView()));

  const mappedAccounts = accounts.map(mapAccount);

  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-gray-50 dark:bg-gray-900">
      <div className="max-w-7xl mx-auto">
        <CombinedHoldingsClient initialAccounts={mappedAccounts} />
      </div>
    </div>
  );
}
