"use client";

import { useState } from "react";
import AccountsHeader from "@/components/accounts/pure/AccountsHeader";
import AccountsList from "@/components/accounts/pure/AccountsList";
import AddAccountModal from "./AddAccountModal";

interface AccountsProps {
  accounts: Account[];
}

export default function Accounts({ accounts }: AccountsProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCreateAccount = () => {
    setIsModalOpen(true);
  };

  const handleAccountAdded = () => {
    window.location.reload();
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="flex-1 flex font-sans pt-8 bg-white dark:bg-gray-900">
      <div className="flex-1 flex flex-col w-full">
        <div className="flex-none w-1/2 mx-auto">
          <AccountsHeader onCreateAccount={handleCreateAccount} />
        </div>
        <div className="flex-1 flex flex-col w-1/2 mx-auto">
          <AccountsList accounts={accounts} />
        </div>
      </div>
      <AddAccountModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onAccountAdded={handleAccountAdded}
      />
    </div>
  );
}
