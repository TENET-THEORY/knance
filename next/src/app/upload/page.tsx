"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import CsvUpload from "@/components/upload/client/CsvUpload";

export default function UploadPage() {
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const router = useRouter();

  const handleUploadSuccess = () => {
    setUploadSuccess(true);
    // Redirect to accounts page after successful upload
    setTimeout(() => {
      router.push("/accounts");
    }, 2000);
  };

  return (
    <div className="font-sans min-h-screen p-8 pt-8 bg-white dark:bg-gray-900">
      <div className="max-w-4xl mx-auto">
        <div className="mb-8">
          <h1 className="text-4xl font-bold mb-4 text-gray-900 dark:text-white">
            Upload CSV Files
          </h1>
          <p className="text-xl text-gray-600 dark:text-gray-300">
            Upload your position data from Interactive Brokers or Charles Schwab
          </p>
        </div>

        {uploadSuccess && (
          <div className="mb-6 p-4 bg-green-50 dark:bg-green-900/20 border border-green-200 dark:border-green-800 rounded-md">
            <p className="text-green-800 dark:text-green-200">
              Upload successful! Redirecting to accounts page...
            </p>
          </div>
        )}

        <CsvUpload onUploadSuccess={handleUploadSuccess} />

        <div className="mt-8 p-6 bg-gray-50 dark:bg-gray-800 rounded-lg">
          <h3 className="text-lg font-semibold text-gray-800 dark:text-white mb-3">
            Instructions
          </h3>
          <div className="space-y-2 text-sm text-gray-600 dark:text-gray-300">
            <p>
              <strong>Interactive Brokers:</strong> Export your positions as CSV
              from the TWS or Client Portal
            </p>
            <p>
              <strong>Charles Schwab:</strong> Export your positions as CSV from
              the Schwab website
            </p>
            <p>
              • Drag and drop your CSV file onto the upload area, or click to
              select
            </p>
            <p>• Only CSV files are accepted</p>
            <p>• Your data will be processed and added to your accounts</p>
          </div>
        </div>
      </div>
    </div>
  );
}
