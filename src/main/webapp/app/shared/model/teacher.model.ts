import { IUser } from 'app/core/user/user.model';

export interface ITeacher {
  id?: number;
  tuition?: string;
  user?: IUser;
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public tuition?: string,
    public user?: IUser,
  ) {}
}
