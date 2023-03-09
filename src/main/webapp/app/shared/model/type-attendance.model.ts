export interface ITypeAttendance {
  id?: number;
  name?: string;
  description?: string;
}

export class TypeAttendance implements ITypeAttendance {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
