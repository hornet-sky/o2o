function isCustomer(userType) {
    if(userType == null || userType == undefined) {
        return false;
    }
    return (userType & 0x01) === 1;
}
function isShopOwner(userType) {
    if(userType == null || userType == undefined) {
        return false;
    }
    return (userType & 0x02) === 2;
}
function isSuperAdministrator(userType) {
    if(userType == null || userType == undefined) {
        return false;
    }
    return (userType & 0x08) === 8;
}