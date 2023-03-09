import { IStudent } from 'app/shared/model/student.model';
import { INGroup } from 'app/shared/model/n-group.model';

export interface IGroupStudent {
  id?: number;
  student?: IStudent;
  nGroup?: INGroup;
  opportunity1?: number;
  opportunity2?: number;
}

export class GroupStudent implements IGroupStudent {
  constructor(
    public id?: number,
    public student?: IStudent,
    public nGroup?: INGroup,
    public opportunity1?: number,
    public opportunity2?: number
  ) {}
}
