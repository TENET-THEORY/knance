"use client"

import CombinedHoldingsClient from "@/components/holdings/client/CombinedHoldingsClient";
import {useEffect, useState} from "react";

export default function CombinedHoldingsPage() {
  const [ accounts, setAccounts ] = useState<Account[]>([]);

  useEffect(() => {
    setupSocket();
  }, [])

  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-gray-50 dark:bg-gray-900">
      <div className="max-w-7xl mx-auto">
        <CombinedHoldingsClient accounts={accounts} />
      </div>
    </div>
  );

  function setupSocket() {
    let serverURL = 'ws://0.0.0.0:8080/accounts/full';
    let socket = new WebSocket(serverURL);

    socket.onopen = logOpenToConsole;
    socket.onclose = logCloseToConsole;
    socket.onmessage = readAndDisplayTask;
  }
  function readAndDisplayTask(event: MessageEvent<string>) {
    console.log(`Received ${event.data}`);
    setAccounts(JSON.parse(event.data));
  }

  function logCloseToConsole() {
    console.log("Web socket connection closed");
  }

  function logOpenToConsole() {
    console.log("Web socket connection opened");
  }
}
