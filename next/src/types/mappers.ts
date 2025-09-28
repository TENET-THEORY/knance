import {
  Cash as KCash,
  Stock as KStock,
  Etf as KEtf,
  AssetType as KAssetType,
  PriceData as KPriceData,
  CreditCard as KCreditCard,
  Account as KAccount,
} from "@common/api";

export function mapAccount(account: KAccount): Account {
  return {
    id: account.id,
    name: account.name,
    cashHoldings: Array.from(account.cashHoldings.asJsReadonlyArrayView()).map(
      (cash: KCash): Cash => mapCash(cash),
    ),
    stockHoldings: Array.from(
      account.stockHoldings.asJsReadonlyArrayView(),
    ).map((stock: KStock): Stock => mapStock(stock)),
    etfHoldings: Array.from(account.etfHoldings.asJsReadonlyArrayView()).map(
      (etf: KEtf): Etf => mapEtf(etf),
    ),
    creditCards: Array.from(account.creditCards.asJsReadonlyArrayView()).map(
      (creditCard: KCreditCard): CreditCard => mapCreditCard(creditCard),
    ),
  };
}

function mapCash(cash: KCash): Cash {
  return {
    id: cash.id,
    amount: cash.amount,
    currency: cash.currency,
    usdValue: cash.usdValue,
  };
}

function mapStock(stock: KStock): Stock {
  return {
    id: stock.id,
    symbol: stock.symbol,
    quantity: stock.quantity,
    costBasis: stock.costBasis,
    priceData: mapPriceData(stock.priceData),
    divYield: stock.divYield,
    peRatio: stock.peRatio,
    earningsYield: stock.earningsYield,
  };
}

function mapEtf(etf: KEtf): Etf {
  return {
    id: etf.id,
    symbol: etf.symbol,
    quantity: etf.quantity,
    costBasis: etf.costBasis,
    priceData: mapPriceData(etf.priceData),
    assetType: mapAssetType(etf.assetType),
  };
}

function mapAssetType(assetType: KAssetType): AssetType {
  return {
    name: assetType.name,
  };
}

function mapCreditCard(creditCard: KCreditCard): CreditCard {
  return {
    id: creditCard.id,
    balance: creditCard.balance,
  };
}

function mapPriceData(
  priceData: KPriceData | null | undefined,
): PriceData | null {
  if (!priceData) return null;
  return {
    currentPrice: priceData.currentPrice,
    marketValue: priceData.marketValue,
    gain: priceData.gain,
    percentGain: priceData.percentGain,
    dailyGain: priceData.dailyGain,
    dailyPercentGain: priceData.dailyPercentGain,
  };
}

export function mapSecurityToHolding(security: Security): Holding {
  return {
    symbol: security.symbol,
    quantity: security.quantity,
    costBasis: security.costBasis,
    currentPrice: security.priceData?.currentPrice ?? null,
    marketValue: security.priceData?.marketValue ?? null,
    gain: security.priceData?.gain ?? null,
    percentGain: security.priceData?.percentGain ?? null,
    dailyGain: security.priceData?.dailyGain ?? null,
    dailyPercentGain: security.priceData?.dailyPercentGain ?? null,
  };
}
