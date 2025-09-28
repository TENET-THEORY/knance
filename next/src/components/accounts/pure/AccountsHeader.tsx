export default function AccountsHeader({
  onCreateAccount,
}: {
  onCreateAccount: () => void;
}) {
  return (
    <div className="flex flex-row justify-between w-full mb-8">
      <h1 className="text-3xl font-bold text-gray-800 dark:text-white">
        Accounts
      </h1>
      <button
        className="bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-800 text-white font-medium py-2 px-4 rounded-lg transition-colors"
        onClick={onCreateAccount}
      >
        Create Account
      </button>
    </div>
  );
}
