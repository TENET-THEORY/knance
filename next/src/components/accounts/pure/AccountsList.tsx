import Link from "next/link";

interface AccountsListProps {
  accounts: Account[];
}

export default function AccountsList({accounts}: AccountsListProps) {
  return (

    <div className="flex-1 flex flex-col">
      {accounts.length === 0 ? (
        <div className="text-center text-gray-500 dark:text-gray-400 mt-8">
          No accounts found.
        </div>
      ) : (
        <div className="flex-1 flex flex-col gap-2 p-4 w-full items-center">
          {accounts.map((account) => (
            <Link className="flex-1 flex items-center justify-center  w-full dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-lg p-4 hover:shadow-md transition-shadow cursor-pointer text-lg font-semibold text-gray-800 dark:text-white" key={account.id} href={`/accounts/${account.id}`}>
                  {account.name}
            </Link>
          ))}
        </div>
      )
      }
    </div>
  );
}
