export interface IRoom {
  id?: number;
  name?: string;
}

export class Room implements IRoom {
  constructor(public id?: number, public name?: string) {}
}
