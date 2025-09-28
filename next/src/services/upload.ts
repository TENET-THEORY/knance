"use server";

const backendUrl = process.env.BACKEND_URL || "http://localhost:8080";

export async function uploadIbkrFile(
  file: File,
): Promise<{ success: boolean; message: string }> {
  const formData = new FormData();
  formData.append("file", file);

  try {
    const response = await fetch(`${backendUrl}/upload/ibkr`, {
      method: "POST",
      body: formData,
    });

    if (!response.ok) {
      const errorData = await response
        .json()
        .catch(() => ({ error: "Upload failed" }));
      return { success: false, message: errorData.error || "Upload failed" };
    }

    const result = await response.json();
    return {
      success: result.success,
      message: result.message || "IBKR CSV uploaded successfully",
    };
  } catch (error) {
    return {
      success: false,
      message:
        error instanceof Error ? error.message : "Failed to upload IBKR CSV",
    };
  }
}

export async function uploadSchwabFile(
  file: File,
): Promise<{ success: boolean; message: string }> {
  const formData = new FormData();
  formData.append("file", file);

  try {
    const response = await fetch(`${backendUrl}/upload/schwab`, {
      method: "POST",
      body: formData,
    });

    if (!response.ok) {
      const errorData = await response
        .json()
        .catch(() => ({ error: "Upload failed" }));
      return { success: false, message: errorData.error || "Upload failed" };
    }

    const result = await response.json();
    return {
      success: true,
      message: result.message || "Schwab CSV uploaded successfully",
    };
  } catch (error) {
    return {
      success: false,
      message:
        error instanceof Error ? error.message : "Failed to upload Schwab CSV",
    };
  }
}
