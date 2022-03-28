export interface IPersonCompanyHistory {
  id?: number;
  companyId?: number | null;
  personId?: number | null;
  investor?: boolean | null;
  founder?: boolean | null;
}

export const defaultValue: Readonly<IPersonCompanyHistory> = {
  investor: false,
  founder: false,
};
