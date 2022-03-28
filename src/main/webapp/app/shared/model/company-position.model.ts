import { IJob } from 'app/shared/model/job.model';

export interface ICompanyPosition {
  id?: number;
  name?: string;
  jobs?: IJob[] | null;
}

export const defaultValue: Readonly<ICompanyPosition> = {};
