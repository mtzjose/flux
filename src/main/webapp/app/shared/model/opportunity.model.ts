import dayjs from 'dayjs';
import { ICompany } from 'app/shared/model/company.model';
import { IContactSource } from 'app/shared/model/contact-source.model';
import { IProcessStage } from 'app/shared/model/process-stage.model';

export interface IOpportunity {
  id?: number;
  companyId?: number | null;
  applyDate?: string | null;
  contactSourceId?: number | null;
  processStageId?: number | null;
  companyId?: ICompany | null;
  contactSourceId?: IContactSource | null;
  processStageId?: IProcessStage | null;
}

export const defaultValue: Readonly<IOpportunity> = {};
