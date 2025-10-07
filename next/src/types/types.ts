interface PriceData {
  currentPrice: number;
  marketValue: number;
  gain: number;
  percentGain: number;
  dailyGain: number | null | undefined;
  dailyPercentGain: number | null | undefined;
}

interface Security {
  id: number | null | undefined;
  symbol: string;
  quantity: number;
  costBasis: number;
  priceData: PriceData | null | undefined;
}

interface Stock extends Security {
  divYield: number | null | undefined;
  peRatio: number | null | undefined;
  earningsYield: number | null | undefined;
}

interface Etf extends Security {
  assetType: AssetType;
}

interface Holding {
  symbol: string;
  quantity: number;
  costBasis: number;
  currentPrice: number | null;
  marketValue: number | null;
  gain: number | null;
  percentGain: number | null;
  dailyGain: number | null;
  dailyPercentGain: number | null;
  earningsYield?: number | null;
  divYield?: number | null;
  peRatio?: number | null;
}

interface AssetType {
  name: string;
}

interface Cash {
  id: number | null | undefined;
  amount: number;
  currency: string;
  usdValue: number | null | undefined;
}

interface CreditCard {
  id: number | null | undefined;
  balance: number;
}

interface Account {
  id: number | null | undefined;
  name: string;
  cashHoldings: Cash[];
  stockHoldings: Stock[];
  etfHoldings: Etf[];
  creditCards: CreditCard[];
}

// WebSocket Message Types
interface Quote {
  symbol: string;
  price: number;
  dailyGain: number | null;
  dailyPercentGain: number | null;
  timestamp: string;
}

interface BulkQuoteMessage {
  type: string;
  quotes: Quote[] | null;
  symbols: string[] | null;
}

interface AllAccountsWithHoldingsMessage {
  type: string;
  accounts: Account[] | null;
  bulkQuoteMessage: BulkQuoteMessage | null;
}
