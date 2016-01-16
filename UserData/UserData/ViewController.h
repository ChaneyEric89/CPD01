//
//  ViewController.h
//  UserData
//
//  Created by Eric Chaney on 1/13/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>


@interface ViewController : UIViewController

- (IBAction)signUp:(UIButton *)sender;

- (IBAction)logIn:(UIButton *)sender;

@property (strong, nonatomic) IBOutlet UITextField *userEnterField;
@property (strong, nonatomic) IBOutlet UITextField *passEnterField;

@end

