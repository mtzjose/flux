import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import person from 'app/entities/person/person.reducer';
// prettier-ignore
import company from 'app/entities/company/company.reducer';
// prettier-ignore
import opportunity from 'app/entities/opportunity/opportunity.reducer';
// prettier-ignore
import address from 'app/entities/address/address.reducer';
// prettier-ignore
import job from 'app/entities/job/job.reducer';
// prettier-ignore
import founder from 'app/entities/founder/founder.reducer';
// prettier-ignore
import companyPosition from 'app/entities/company-position/company-position.reducer';
// prettier-ignore
import city from 'app/entities/city/city.reducer';
// prettier-ignore
import country from 'app/entities/country/country.reducer';
// prettier-ignore
import gender from 'app/entities/gender/gender.reducer';
// prettier-ignore
import processStage from 'app/entities/process-stage/process-stage.reducer';
// prettier-ignore
import collegeDegree from 'app/entities/college-degree/college-degree.reducer';
// prettier-ignore
import pronoun from 'app/entities/pronoun/pronoun.reducer';
// prettier-ignore
import school from 'app/entities/school/school.reducer';
// prettier-ignore
import race from 'app/entities/race/race.reducer';
// prettier-ignore
import contactSource from 'app/entities/contact-source/contact-source.reducer';
// prettier-ignore
import companyCategory from 'app/entities/company-category/company-category.reducer';
// prettier-ignore
import companyCategories from 'app/entities/company-categories/company-categories.reducer';
// prettier-ignore
import jobHistory from 'app/entities/job-history/job-history.reducer';
// prettier-ignore
import personCompanyHistory from 'app/entities/person-company-history/person-company-history.reducer';
// prettier-ignore
import employeeRange from 'app/entities/employee-range/employee-range.reducer';
// prettier-ignore
import founderPositions from 'app/entities/founder-positions/founder-positions.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  person,
  company,
  opportunity,
  address,
  job,
  founder,
  companyPosition,
  city,
  country,
  gender,
  processStage,
  collegeDegree,
  pronoun,
  school,
  race,
  contactSource,
  companyCategory,
  companyCategories,
  jobHistory,
  personCompanyHistory,
  employeeRange,
  founderPositions,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
