package de.tum.in.far.threedui.control.general;


public enum Motion {
	
	Forward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a==0 && b > 0)
				return true;
			return false;		
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.forward();			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveForward(angle);
			
		}

		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(0);
			mRevolver.displayModel(0);
			ci = 0;
			
		}
		
	}, Backward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a==0 && b < 0)
				return true;
			return false;	
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.backward();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveBackward(angle);
			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(4);
			mRevolver.displayModel(4);
			ci = 4;			
		}
		
	}, Right {
		@Override
		public boolean checkSignal(double a, double b) {
			if(b==0 && a < 0)
				return true;
			return false;	
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.right();			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveRight(angle);
			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(6);
			mRevolver.displayModel(6);
			ci = 6;			
		}
		
	}, Left {
		@Override
		public boolean checkSignal(double a, double b) {
			if(b==0 && a > 0)
				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.left();			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveLeft(angle);			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(2);
			mRevolver.displayModel(2);
			ci = 2;			
		}
	}
	, LeftBackward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a > 0 && b < 0)
				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.leftBackward();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveLeftBackward(angle);
			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(3);
			mRevolver.displayModel(3);
			ci = 3;			
		}
	}, LeftForward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a > 0 && b > 0)
				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.leftForward();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveLeftForward(angle);
			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(1);
			mRevolver.displayModel(1);
			ci = 1;			
		}
	}, RightBackward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a < 0 && b < 0)
				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.rightBackward();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveRightBackward(angle);
			
		}
			
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(5);
			mRevolver.displayModel(5);
			ci = 5;			
		}
	}, RightFoward {
		@Override
		public boolean checkSignal(double a, double b) {
			if(a < 0 && b > 0)
				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.rightForward();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			mWrap.moveRightForward(angle);			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			System.out.println(7);
			mRevolver.displayModel(7);
			ci = 7;			
		}
	}, Stop {
		@Override
		public boolean checkSignal(double a, double b) {
//			if (a==0 && b==0)
//				return true;
			return false;
		}

		@Override
		public void switchIndicator(Indicator is) {
			is.stop();
			
		}

		@Override
		public void move(MovementWrapper mWrap, AngleTuple angle) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void display(ModelRevolver mRevolver) {
			mRevolver.displayModel(ci);
			
		}

	};

	public abstract boolean checkSignal(double a, double b);
	public abstract void switchIndicator(Indicator indicator);
	public abstract void move(MovementWrapper mWrap, AngleTuple angle);
	public abstract void display(ModelRevolver mRevolver);
	public static Motion getMoveDirection(double a , double b){
		for (Motion m : Motion.values()) {
			if(m.checkSignal(a, b))
				return m;
		}
		
		return null;
	}
	private static int ci;
	public static int getIndex(){
		return ci;
	}
}
