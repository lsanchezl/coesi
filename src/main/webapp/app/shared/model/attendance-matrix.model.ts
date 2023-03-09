export interface IAttendanceMatrix {
  headers?: string[];
  data?: string[][];
  ids?: string[][];
}

export class AttendanceMatrix implements IAttendanceMatrix {
  constructor(public headers?: string[], public data?: string[][], public ids?: string[][]) {}
}
