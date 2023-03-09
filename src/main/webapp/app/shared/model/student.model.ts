import { IUser } from 'app/core/user/user.model';
import { IGeneration } from 'app/shared/model/generation.model';
import { ICareer } from 'app/shared/model/career.model';

export interface IStudent {
  id?: number;
  tuition?: string;
  graduationSermon?: number;
  user?: IUser;
  generation?: IGeneration;
  career?: ICareer;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public tuition?: string,
    public graduationSermon?: number,
    public user?: IUser,
    public generation?: IGeneration,
    public career?: ICareer
  ) {}
}
