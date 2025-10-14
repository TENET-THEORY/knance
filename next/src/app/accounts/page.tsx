import {AccountsViewModel} from "knance-common-api";
import AccountsHeader from "@/components/accounts/pure/AccountsHeader";
import AccountsList from "@/components/accounts/pure/AccountsList";
import Accounts from "@/components/accounts/client/Accounts";
import {mapAccount} from "@/types/mappers";

export default async function Page() {
  const vm = new AccountsViewModel();
  const accounts = await vm
    .fetchAccounts()
    .then((accounts) => Array.from(accounts.asJsReadonlyArrayView()));

  return (
    <Accounts accounts={accounts.map(mapAccount)} />
  );
}
