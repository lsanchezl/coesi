import { Moment } from 'moment';
import { ITypeAttendance } from 'app/shared/model/type-attendance.model';
import { IGroupStudent } from 'app/shared/model/group-student.model';

export interface IAttendance {
  id?: number;
  dateAttendance?: Moment;
  typeAttendance?: ITypeAttendance;
  groupStudent?: IGroupStudent;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public dateAttendance?: Moment,
    public typeAttendance?: ITypeAttendance,
    public groupStudent?: IGroupStudent
  ) {}
}
