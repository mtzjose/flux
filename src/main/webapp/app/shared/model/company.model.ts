import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IEmployeeRange } from 'app/shared/model/employee-range.model';
import { IJob } from 'app/shared/model/job.model';

export interface ICompany {
  id?: number;
  meta?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  color?: string | null;
  name?: string | null;
  legalName?: string | null;
  oneLiner?: string | null;
  description?: string | null;
  foundingDate?: string | null;
  socialLinks?: string | null;
  addressId?: number | null;
  employeeRange?: number | null;
  addressId?: IAddress | null;
  employeeRange?: IEmployeeRange | null;
  jobs?: IJob[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
