export interface ISchoolCycle {
  id?: number;
  name?: string;
}

export class SchoolCycle implements ISchoolCycle {
  constructor(public id?: number, public name?: string) {}
}
