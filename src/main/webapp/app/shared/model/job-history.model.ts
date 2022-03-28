import dayjs from 'dayjs';

export interface IJobHistory {
  id?: number;
  jobId?: number | null;
  personId?: number | null;
  startDate?: string | null;
  endDate?: string | null;
}

export const defaultValue: Readonly<IJobHistory> = {};
