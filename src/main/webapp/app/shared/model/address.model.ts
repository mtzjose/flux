export interface IAddress {
  id?: number;
  countryId?: number;
  cityId?: number;
  streetAddress?: string | null;
  postalCode?: string | null;
}

export const defaultValue: Readonly<IAddress> = {};
