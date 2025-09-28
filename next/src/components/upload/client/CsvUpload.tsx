"use client";

import { useState, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { uploadIbkrFile, uploadSchwabFile } from "@/services/upload";

interface CsvUploadProps {
  onUploadSuccess?: () => void;
}

export default function CsvUpload({ onUploadSuccess }: CsvUploadProps) {
  const [uploading, setUploading] = useState(false);
  const [message, setMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleFileUpload = async (
    file: File,
    uploadType: "ibkr" | "schwab",
  ) => {
    if (!file) return;

    setUploading(true);
    setError(null);
    setMessage(null);

    try {
      let response;
      if (uploadType === "ibkr") {
        response = await uploadIbkrFile(file);
      } else {
        response = await uploadSchwabFile(file);
      }

      if (response.success) {
        setMessage(response.message);
        onUploadSuccess?.();
      } else {
        setError(response.message);
      }
    } catch (err) {
      setError("Failed to upload CSV file");
      console.error("Upload error:", err);
    } finally {
      setUploading(false);
    }
  };

  const onIbkrDrop = useCallback(async (acceptedFiles: File[]) => {
    if (acceptedFiles.length === 0) return;

    const file = acceptedFiles[0];
    if (!file.name.endsWith(".csv")) {
      setError("Please upload a CSV file");
      return;
    }

    await handleFileUpload(file, "ibkr");
  }, []);

  const onSchwabDrop = useCallback(async (acceptedFiles: File[]) => {
    if (acceptedFiles.length === 0) return;

    const file = acceptedFiles[0];
    if (!file.name.endsWith(".csv")) {
      setError("Please upload a CSV file");
      return;
    }

    await handleFileUpload(file, "schwab");
  }, []);

  const {
    getRootProps: getIbkrRootProps,
    getInputProps: getIbkrInputProps,
    isDragActive: isIbkrDragActive,
  } = useDropzone({
    onDrop: onIbkrDrop,
    accept: {
      "text/csv": [".csv"],
    },
    maxFiles: 1,
  });

  const {
    getRootProps: getSchwabRootProps,
    getInputProps: getSchwabInputProps,
    isDragActive: isSchwabDragActive,
  } = useDropzone({
    onDrop: onSchwabDrop,
    accept: {
      "text/csv": [".csv"],
    },
    maxFiles: 1,
  });

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6 border border-gray-200 dark:border-gray-700">
      <h2 className="text-2xl font-semibold text-gray-800 dark:text-white mb-6">
        Upload CSV Files
      </h2>

      <div className="space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
            Interactive Brokers (IBKR) Positions
          </label>
          <div
            {...getIbkrRootProps()}
            className={`border-2 border-dashed rounded-lg p-6 text-center cursor-pointer transition-colors
              ${isIbkrDragActive ? "border-blue-500 bg-blue-50 dark:bg-blue-900/20" : "border-gray-300 dark:border-gray-600 hover:border-gray-400 dark:hover:border-gray-500"}
              ${uploading ? "opacity-50 cursor-not-allowed" : ""}`}
          >
            <input {...getIbkrInputProps()} disabled={uploading} />
            <div className="space-y-3">
              <div className="text-gray-600 dark:text-gray-300">
                {isIbkrDragActive ? (
                  <p>Drop the IBKR CSV file here</p>
                ) : (
                  <p>
                    Drag and drop your IBKR CSV file here, or click to select
                  </p>
                )}
              </div>
              <button
                type="button"
                disabled={uploading}
                className="px-4 py-2 bg-blue-500 dark:bg-blue-600 text-white rounded-md hover:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50"
              >
                Select IBKR File
              </button>
            </div>
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
            Charles Schwab Positions
          </label>
          <div
            {...getSchwabRootProps()}
            className={`border-2 border-dashed rounded-lg p-6 text-center cursor-pointer transition-colors
              ${isSchwabDragActive ? "border-green-500 bg-green-50 dark:bg-green-900/20" : "border-gray-300 dark:border-gray-600 hover:border-gray-400 dark:hover:border-gray-500"}
              ${uploading ? "opacity-50 cursor-not-allowed" : ""}`}
          >
            <input {...getSchwabInputProps()} disabled={uploading} />
            <div className="space-y-3">
              <div className="text-gray-600 dark:text-gray-300">
                {isSchwabDragActive ? (
                  <p>Drop the Schwab CSV file here</p>
                ) : (
                  <p>
                    Drag and drop your Schwab CSV file here, or click to select
                  </p>
                )}
              </div>
              <button
                type="button"
                disabled={uploading}
                className="px-4 py-2 bg-green-500 dark:bg-green-600 text-white rounded-md hover:bg-green-600 dark:hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 disabled:opacity-50"
              >
                Select Schwab File
              </button>
            </div>
          </div>
        </div>
      </div>

      {uploading && (
        <div className="mt-4 flex items-center text-blue-600 dark:text-blue-400">
          <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 dark:border-blue-400 mr-2"></div>
          Uploading and processing CSV file...
        </div>
      )}

      {message && (
        <div className="mt-4 p-3 bg-green-50 dark:bg-green-900/20 border border-green-200 dark:border-green-800 rounded-md">
          <p className="text-green-800 dark:text-green-200 text-sm">
            {message}
          </p>
        </div>
      )}

      {error && (
        <div className="mt-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-md">
          <p className="text-red-800 dark:text-red-200 text-sm">{error}</p>
        </div>
      )}

      <div className="mt-4 text-xs text-gray-500 dark:text-gray-400">
        <p>
          <strong>IBKR:</strong> Upload your Interactive Brokers positions CSV
          export
        </p>
        <p>
          <strong>Schwab:</strong> Upload your Charles Schwab positions CSV
          export
        </p>
      </div>
    </div>
  );
}
