export class SimpleBooking {
  userId: number;
  tourId: number;
  status: boolean;

  public constructor(user: number, tour: number, active: boolean) {
    this.userId = user;
    this.tourId = tour;
    this.status = active;
  }
}
