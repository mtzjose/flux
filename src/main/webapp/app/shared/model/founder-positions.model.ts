export interface IFounderPositions {
  id?: number;
  positionId?: number | null;
  companyId?: number | null;
}

export const defaultValue: Readonly<IFounderPositions> = {};
