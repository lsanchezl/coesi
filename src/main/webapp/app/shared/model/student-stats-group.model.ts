import { IAttendanceMatrix } from 'app/shared/model/attendance-matrix.model';

export interface IStudentStatsGroup {
  attendanceMatrix?: IAttendanceMatrix;
  evaluation?: string[][];
}

export class StudentStatsGroup implements IStudentStatsGroup {
  constructor(
   public attendanceMatrix?: IAttendanceMatrix,
   public evaluation?: string[][]
   ) {}
}
