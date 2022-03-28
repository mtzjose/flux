import dayjs from 'dayjs';
import { ISchool } from 'app/shared/model/school.model';
import { ICollegeDegree } from 'app/shared/model/college-degree.model';
import { ICountry } from 'app/shared/model/country.model';
import { IGender } from 'app/shared/model/gender.model';
import { IPronoun } from 'app/shared/model/pronoun.model';
import { IRace } from 'app/shared/model/race.model';
import { IAddress } from 'app/shared/model/address.model';

export interface IPerson {
  id?: number;
  meta?: string | null;
  profilePictureContentType?: string | null;
  profilePicture?: string | null;
  firstname?: string | null;
  lastname?: string | null;
  middlename?: string | null;
  bio?: string | null;
  school?: number | null;
  major?: number | null;
  socialLinks?: string | null;
  nationalityId?: number | null;
  genderId?: number | null;
  pronounId?: number | null;
  raceId?: number | null;
  addressId?: number | null;
  birthdate?: string | null;
  school?: ISchool | null;
  major?: ICollegeDegree | null;
  nationalityId?: ICountry | null;
  genderId?: IGender | null;
  pronounId?: IPronoun | null;
  raceId?: IRace | null;
  addressId?: IAddress | null;
}

export const defaultValue: Readonly<IPerson> = {};
