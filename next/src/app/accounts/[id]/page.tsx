import ErrorMessage from "@/components/common/pure/ErrorMessage";
import AccountPageClient from "@/components/account/client/AccountPageClient";
import { AccountsViewModel } from "@common/api";
import { mapAccount } from "@/types/mappers";

export default async function Page({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const vm = new AccountsViewModel();
  const account = await vm.fetchAccount(parseInt(id));

  if (!account) {
    return (
      <ErrorMessage
        message="No account data available"
        onRetry={() => window.location.reload()}
      />
    );
  }

  return (
    <AccountPageClient
      initialAccount={mapAccount(account)}
      accountId={parseInt(id)}
    />
  );
}
