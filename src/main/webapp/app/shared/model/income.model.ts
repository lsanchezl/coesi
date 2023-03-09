import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IConceptIncome } from 'app/shared/model/concept-income.model';

export interface IIncome {
  id?: number;
  amount?: number;
  comments?: string;
  applicationDate?: Moment;
  creationDate?: Moment;
  modificationDate?: Moment;
  user?: IUser;
  creationUser?: IUser;
  modificationUser?: IUser;
  conceptIncome?: IConceptIncome;
}

export class Income implements IIncome {
  constructor(
    public id?: number,
    public amount?: number,
    public comments?: string,
    public applicationDate?: Moment,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public user?: IUser,
    public creationUser?: IUser,
    public modificationUser?: IUser,
    public conceptIncome?: IConceptIncome
  ) {}
}
