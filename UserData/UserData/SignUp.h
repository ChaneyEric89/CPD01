//
//  SignUp.h
//  UserData
//
//  Created by Eric Chaney on 1/11/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#ifndef SignUp_h
#define SignUp_h


#endif /* SignUp_h */
#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface SignUp : UIViewController

- (IBAction)submitSignUp:(UIButton *)sender;
-(IBAction)backBtn:(UIButton *)sender;

@property (strong, nonatomic) IBOutlet UITextField *userField;
@property (strong, nonatomic) IBOutlet UITextField *passField;
@property (strong, nonatomic) IBOutlet UITextField *emailField;

@end