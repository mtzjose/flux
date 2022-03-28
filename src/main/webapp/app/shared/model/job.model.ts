import { ICompany } from 'app/shared/model/company.model';
import { ICompanyPosition } from 'app/shared/model/company-position.model';

export interface IJob {
  id?: number;
  companyId?: number;
  jobPositionId?: number;
  companyId?: ICompany | null;
  jobPositionId?: ICompanyPosition | null;
  companyId?: ICompany | null;
  jobPositionId?: ICompanyPosition | null;
}

export const defaultValue: Readonly<IJob> = {};
